CREATE DATABASE IF NOT EXISTS carbonique;

use carbonique;


drop table if exists tbl_unit_conversion;

create table IF NOT EXISTS tbl_unit_conversion
(
    fuel varchar(50) primary key
    ,units varchar(3)
    ,co2_kg float
    ,unit_cost float
    ,unit_sell float default 0
);

INSERT INTO tbl_unit_conversion (units,fuel,co2_kg, unit_cost, unit_sell ) VALUES
     ('kWh' ,'electricity'  , 0.47300, 0.2333,  0.055)
    ,('MJ' ,'gas'           , 0.05553, 0.0431,  0.000) 
    ,('MJ' ,'lpg'           , 0.06420, 0.08,    0.000) /*lpg 2200MJ in a standard 45kg gas bottle at $177.*/
    /*,('MJ' ,'firewood'      , 0.00500)  */
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
   , mj_per_year float
   , area_weight float
);

INSERT INTO tbl_bedrooms (bedrooms, bedroom_label, mj_per_year) values 
     (1, '1' , 2954 )
    ,(2, '2' , 4840 )
    ,(3, '3' , 5077 )
    ,(4, '4' , 5805 )
    ,(5, '5+', 7351 )
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
https://engage.vic.gov.au/download/document/27749 Page 41 (2021)
https://www.sustainability.vic.gov.au/energy-efficiency-and-reducing-emissions/save-energy-in-the-home/reduce-heating-costs-at-home/calculate-heating-costs
*/
INSERT INTO tbl_category_type (category_id,fuel, category_type,relative_efficency,market_share) VALUES
 (1,'gas'			,'Gas Ducted'						,1.00	,0.515)
,(1,'electricity'	,'Split Air Con'					,0.41	,0.310)
,(1,'electricity'	,'Ducted air conditioner'			,0.60	,0.074)
,(1,'gas'			,'Gas space heater' 				,1.02	,0.039)
,(1,'electricity'	,'Fixed or portable electric heater',1.74	,0.040)
,(1,'gas'			,'Gas Hydronic heating'				,0.77	,0.020)
,(1,'electricity'	,'Electric in-slab'					,1.76	,0.002);


/*
HOT WATER
Efficiency : 
Sustainability Victoria Gas hot water systems
https://www.sustainability.vic.gov.au/energy-efficiency-and-reducing-emissions/save-energy-in-the-home/water-heating/choose-the-right-hot-water-system/gas-hot-water-systems

Market share: 
ABS 4602.0.55.001 - Environmental Issues: Energy Use and Conservation, Mar 2014
https://www.abs.gov.au/AUSSTATS/abs@.nsf/DetailsPage/4602.0.55.001Mar%202014?OpenDocument
https://engage.vic.gov.au/download/document/27749 Page 67 (2021)

*/
INSERT INTO tbl_category_type (category_id,fuel, category_type,relative_efficency,market_share) VALUES
     (2,'electricity','Electric Storage'            ,1.27   ,0.151)
    ,(2,'lpg'        ,'LPG - storage'               ,1.00   ,0.022)
    ,(2,'gas'        ,'Natural gas - storage'       ,0.87   ,0.108)
    ,(2,'lpg'        ,'LPG - instant'               ,0.70   ,0.054)
    ,(2,'gas'        ,'Natural gas - instant'       ,0.60   ,0.509)
    ,(2,'electricity','Solar Electric boosted'      ,0.44   ,0.054)
    ,(2,'electricity','Heat Pump'                   ,0.38   ,0.043)
    ,(2,'lpg'        ,'Solar LPG boosted'           ,0.25   ,0.005)
    ,(2,'gas'        ,'Solar Natural gas boosted'   ,0.22   ,0.054)  
;



/*
Ref ABS 2009
number of fridges per home
https://www.abs.gov.au/ausstats/abs@.nsf/Products/7E391A69F25A1F30CA25774A0013BF89?opendocument
*/
-- fridges
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency,market_share) VALUES
     (3,'1 fridge'          , 1  , 0.74 )
    ,(3,'2 fridges'         , 2  , 0.243 )
    ,(3,'3 or more fridges' , 3  , 0.017 )
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
    add the default Don't Know with the average weight category_type_weight of 1.0
*/
SELECT 'Dont Know' as ' '; -- print message
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency, category_type_weight,market_share)
SELECT category_id
    , "Don't Know" category_type
    , SUM(relative_efficency*market_share)  as relative_efficency
    , 1.0 category_type_weight
    , 1.0 as market_share
FROM tbl_category_type 
group by category_id;



/*for the other category set weights to 1.0 as there are no real sub categories*/
SELECT 'Dont Know Other' as ' '; -- print message
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency, category_type_weight,market_share)
SELECT category_id
    , category as category_type
    , 1.0 as relative_efficency
    , 1.0 as category_type_weight
    , 1.0 as market_share
FROM tbl_category 
WHERE category_id = 8 /*Other*/
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
Cost of panels
https://www.cleanenergycouncil.org.au/consumers/buying-solar/costs-and-savings
Sun hours per day

*/

DROP TABLE IF EXISTS tbl_solar_const;

CREATE TABLE IF NOT EXISTS tbl_solar_const
(
    sun_hours_per_day float
);

INSERT INTO tbl_solar_const (sun_hours_per_day) VALUES (3.6);


DROP TABLE IF EXISTS tbl_solar_system;
CREATE table IF NOT EXISTS tbl_solar_system
(
    kw int PRIMARY KEY,
    Initial_investment int
); 

INSERT INTO tbl_solar_system (kw,Initial_investment)
VALUES 
     (2 , 3750)
    ,(3 , 4250)
    ,(4 , 5000)
    ,(5 , 6250)
    ,(6 , 7000)
    ,(7 , 8250)
    ,(8 , 9250)
    ,(10,10500)
    ,(15,15500)
    ,(20,19500)
;

DROP TABLE IF EXISTS tbl_solar_day;

create table IF NOT EXISTS tbl_solar_day 
(
     hour int PRIMARY KEY
    ,hourly_usage float 
    ,hourly_generation float 
);

INSERT INTO tbl_solar_day (hour, hourly_usage,hourly_generation)
VALUES 
 ( 1		,0.012		,0.000)
,( 2		,0.013		,0.000)
,( 3		,0.013		,0.000)
,( 4		,0.013		,0.000) 
,( 5		,0.013		,0.005) 
,( 6		,0.013		,0.019)
,( 7		,0.106		,0.034)  
,( 8		,0.106		,0.056)
,( 9		,0.106		,0.082)
,(10		,0.032		,0.108)
,(11		,0.032		,0.128)
,(12		,0.032		,0.136)
,(13		,0.032		,0.128)
,(14		,0.032		,0.108)
,(15		,0.032		,0.082)
,(16		,0.043		,0.056)
,(17		,0.085		,0.034)
,(18		,0.106		,0.019)
,(19		,0.085		,0.005)
,(20		,0.043		,0.000)
,(21		,0.013		,0.000)
,(22		,0.013		,0.000)
,(23		,0.013		,0.000)
,(24		,0.012		,0.000)
;



DROP USER IF EXISTS 'spring'@'localhost';
CREATE USER IF NOT EXISTS 'spring'@'localhost' IDENTIFIED BY '4tsZreage1jNz6wn2';
GRANT SELECT ON carbonique.* TO 'spring'@'localhost';
FLUSH PRIVILEGES;



-- Example carbon profile
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
    ;    /*AND ct.category_type_id in (1, 16, 7 , 22, 8)*/


/*example solar*/

WITH user_input as 
( SELECT 30 as daily_avg_usage_kwh)
, curve as
(
    select   
        s.kw
        ,AVG(Initial_investment) as Initial_investment        
        ,Sum(s.kw * c.sun_hours_per_day * sd.hourly_generation) * 365.25 annual_generation
        ,SUM(
                CASE 
                    when s.kw * c.sun_hours_per_day * sd.hourly_generation >= daily_avg_usage_kwh * sd.hourly_usage
                    then daily_avg_usage_kwh * sd.hourly_usage
                    else s.kw * c.sun_hours_per_day * sd.hourly_generation
                end    
            ) * 365.25 
        as annual_self_consumption
    from user_input i, 
        tbl_solar_const c, 
        tbl_solar_system s, 
        tbl_solar_day sd
    group by s.kw
)
select 
      curve.kw
    , curve.Initial_investment
    , ROUND(
            curve.Initial_investment /
            (      curve.annual_self_consumption * u.unit_cost
                + (curve.annual_generation - curve.annual_self_consumption) * u.unit_sell 
            )  
      ,1) as pay_back_period
    ,round(      curve.annual_self_consumption * u.unit_cost
                + (curve.annual_generation - curve.annual_self_consumption) * u.unit_sell 
     ,0) as dollar_reduction_per_year  
    ,ROUND(u.co2_kg * curve.annual_self_consumption,0) as co2_kg_reduction_self
    ,ROUND(u.co2_kg * (curve.annual_generation - curve.annual_self_consumption),0) as co2_kg_reduction_export
from curve , tbl_unit_conversion u 
where u.units = 'kWh';



/*


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
    SELECT 8, 'Other'        

UNION 
    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
        inner join tbl
    WHERE h.bedrooms = 3 
    AND ct.category_type_id = 1 -- heating



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
*/