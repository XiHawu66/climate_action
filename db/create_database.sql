CREATE DATABASE IF NOT EXISTS carbonique;

use carbonique;

drop table if exists tbl_unit_conversion;

create table IF NOT EXISTS tbl_unit_conversion
(
    fuel varchar(50) primary key
    ,units varchar(3)
    ,co2_kg float
    ,unit_cost float
);

INSERT INTO tbl_unit_conversion (units,fuel,co2_kg, unit_cost ) VALUES
     ('kWh' ,'electricity'  , 0.47300, 0.2333)
    ,('MJ' ,'gas'           , 0.05553, 0.0431) 
    ,('MJ' ,'lpg'           , 0.06420, 0.08) /*lpg 2200MJ in a standard 45kg gas bottle at $177.*/
    /*,('MJ' ,'firewood'      , 0.00500)*/  
;

  




drop table if exists tbl_household;

Create table IF NOT EXISTS tbl_household 
(
     people int PRIMARY KEY
   , house_hold_size_label varchar(3)
   , kwh_per_year float
);

INSERT INTO tbl_household (people, house_hold_size_label, kwh_per_year) values 
     (1, '1' , 2954)
    ,(2, '2' , 4840)
    ,(3, '3' , 5077)
    ,(4, '4' , 5805)
    ,(5, '5+', 7351)
;


/*
House Size for Heating weighting
https://www.abs.gov.au/census/find-census-data/quickstats/2016/2GMEL
https://www.abs.gov.au/ausstats/abs@.nsf/Lookup/2901.0Chapter44102016
*/
drop table if exists tbl_bedrooms;

Create table IF NOT EXISTS tbl_bedrooms 
(
     bedrooms int PRIMARY KEY
   , bedroom_label varchar(3)
   , area_weight float
);

INSERT INTO tbl_bedrooms (bedrooms, bedroom_label, area_weight) values 
     (1, '1' , 2954)
    ,(2, '2' , 4840)
    ,(3, '3' , 5077)
    ,(4, '4' , 5805)
    ,(5, '5+', 7351)
;



DROP table IF EXISTS tbl_category;

Create table IF NOT EXISTS tbl_category
(
     category_id int NOT NULL Primary key
    ,category varchar(255) NOT NULL
    ,category_weight float NOT NULL
);


INSERT INTO tbl_category (category_id,category,category_weight) values 
     (1, 'Heating' , .38) 
    ,(2, 'Hot Water' , .15)
    ,(3, 'Refrigeration' , .11)
    ,(4, 'lighting' , .06)
    ,(5, 'Cooking', .05)
    ,(6, 'Diswasher', .05)
    ,(7, 'Laundry', .05)
;




INSERT INTO tbl_category (category_id,category,category_weight)
SELECT 8, 'Other', round(1- SUM(category_weight),3)
FROM tbl_category;



drop table if exists tbl_category_type;

Create TABLE IF NOT EXISTS tbl_category_type
    (   category_type_id int NOT NULL AUTO_INCREMENT
        ,category_id int
        ,category_type varchar(255)
        ,market_share float
        ,relative_efficency float
        ,category_type_weight float
        ,fuel varchar(50) DEFAULT 'electricity'
        ,reduction_potential float default 0.0
        ,PRIMARY KEY (category_type_id)
    );

-- LIGHTING
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency,market_share) VALUES
     (4,'Incandescent'  , 1.00  , 0.1 )
    ,(4,'Halogen'       , 0.7   , 0.2 )
    ,(4,'CFL'           , 0.22  , 0.5 )
    ,(4,'LED'           , 0.17  , 0.2 )
;

/*
HOT WATER
Efficiency : 
Sustainability Victoria Gas hot water systems
https://www.sustainability.vic.gov.au/energy-efficiency-and-reducing-emissions/save-energy-in-the-home/water-heating/choose-the-right-hot-water-system/gas-hot-water-systems

Market share: 
ABS 4602.0.55.001 - Environmental Issues: Energy Use and Conservation, Mar 2014
https://www.abs.gov.au/AUSSTATS/abs@.nsf/DetailsPage/4602.0.55.001Mar%202014?OpenDocument
*/
INSERT INTO tbl_category_type (category_id,fuel, category_type,relative_efficency,market_share) VALUES
     (2,'electricity','Electric Storage',1.27,0.194)
    ,(2,'lpg','LPG - storage',1,0.04)
    ,(2,'gas','Natural gas - storage',0.87,0.15)
    ,(2,'lpg','LPG - instant',0.7,0.04)
    ,(2,'gas','Natural gas - instant',0.6,0.6)
    ,(2,'electricity','Solar Electric boosted',0.44,0.028)
    ,(2,'electricity','Heat Pump',0.38,0.02)
    ,(2,'lpg','Solar LPG boosted',0.25,0.005)
    ,(2,'gas','Solar Natural gas boosted',0.22,0.028)  
;


/*
for each category
    Calculate the category_type_weight of each product within the category
*/
WITH cat as 
(
    SELECT category_id, SUM(relative_efficency*market_share) as cat_default_weight
    FROM tbl_category_type 
    group by category_id
)
UPDATE tbl_category_type
INNER JOIN cat on tbl_category_type.category_id = cat.category_id
SET tbl_category_type.category_type_weight  = tbl_category_type.relative_efficency / cat.cat_default_weight ;
;


/*
for each category
    for each method 
        Calculate the % saving moving from to the efficient electric method 
*/
with cat_min_electric as
(
    SELECT category_id, MIN(relative_efficency) as relative_efficency
    FROM tbl_category_type 
    where fuel = 'electricity'
    group by category_id    
)
UPDATE tbl_category_type
INNER JOIN cat_min_electric on tbl_category_type.category_id = cat_min_electric.category_id
SET 
    tbl_category_type.reduction_potential  = 1 - cat_min_electric.relative_efficency /tbl_category_type.relative_efficency
WHERE cat_min_electric.relative_efficency < tbl_category_type.relative_efficency
;





/*
for each category
    add the default Don't Know with the average weight category_type_weight of 1.0
*/
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency, category_type_weight)
SELECT category_id
    , "Don't Know" category_type
    , SUM(relative_efficency*market_share)  as relative_efficency
    , 1.0 category_type_weight
FROM tbl_category_type 
group by category_id;

-- Example 

    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * c.category_weight * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND (   ct.category_type_id = 1
        OR  ct.category_type_id = 7
        )


-- Create table bedrooms
-- 1
-- 2
-- 3 bedrooms 
-- 4
-- 5

/*
--,hot_water_category_type_id INT
--,heating_category_type_id INT 

DROP PROCEDURE IF NOT EXISTS sp_profile

DELIMITER //
CREATE PROCEDURE IF NOT EXISTS sp_profile
(     IN number_of_people INT
    , IN house_area_int
    , IN lighting_category_type_id INT
    , IN hot_water_category_type_id INT
    , IN heating_category_type_id INT
) 
BEGIN 
    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * c.category_weight * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = number_of_people 
    AND (   ct.category_type_id = lighting_category_type_id
        OR  hot_water_category_type_id
        )
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE IF NOT EXISTS sp_people
(     IN number_of_people INT) 
BEGIN
    select * 
    from tbl_household h 
    where h.people = number_of_people 
END //
DELIMITER ;




    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * c.category_weight * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND ct.category_type_id in (1,7)

UNION 
    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * c.category_weight * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND ct.category_type_id = 7

*/

DROP USER IF EXISTS 'spring'@'localhost';
CREATE USER IF NOT EXISTS 'spring'@'localhost' IDENTIFIED BY '4tsZreage1jNz6wn2';
GRANT SELECT ON carbonique.* TO 'spring'@'localhost';
FLUSH PRIVILEGES;