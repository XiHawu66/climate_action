CREATE DATABASE IF NOT EXISTS carbonique;
use carbonique;

drop table if exists tbl_unit_conversion;

create table IF NOT EXISTS tbl_unit_conversion
(
    fuel varchar(50) primary key
    ,units varchar(3)
    ,co2_kg float
);

INSERT INTO tbl_unit_conversion (units,fuel,co2_kg) VALUES
     ('kWh' ,'electricity'  , 0.47300)
    ,('MJ' ,'gas'           , 0.05553)
    ,('MJ' ,'lpg'           , 0.06420)
    ,('MJ' ,'firewood'      , 0.00500)
;


drop table if exists tbl_household;

Create table IF NOT EXISTS tbl_household 
(
     people int
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

drop table IF NOT EXISTS tbl_unit_conversion;


drop tabel if exists Create TABLE IF NOT EXISTS tbl_category_type;

Create TABLE IF NOT EXISTS tbl_category_type
    (   category_type_id int NOT NULL AUTO_INCREMENT
        ,category_id int
        ,category_type varchar(255)
        ,marketShare float
        ,relative_efficency float
        ,category_type_weight float
        ,PRIMARY KEY (category_type_id)
    );

INSERT INTO tbl_category_type (category_id,category_type,relative_efficency,marketShare) VALUES
     (4,'Incandescent'  , 1.00  , 0.1 )
    ,(4,'Halogen'       , 0.7   , 0.2 )
    ,(4,'CFL'           , 0.22  , 0.5 )
    ,(4,'LED'           , 0.17  , 0.2 )
;


WITH cat as 
(
    SELECT category_id, SUM(relative_efficency*marketShare) as cat_default_weight
    FROM tbl_category_type 
    group by category_id
)
UPDATE tbl_category_type
INNER JOIN cat on tbl_category_type.category_id = cat.category_id
SET tbl_category_type.category_type_weight  = cat.cat_default_weight / tbl_category_type.relative_efficency

update tbl_category_type
set category_type_weight = 
    (select SUM(sub.relative_efficency*sub.marketShare)
     FROM tbl_category_type as sub
     WHERE sub.category_id = tbl_category_type.category_id
    ) / relative_efficency
;

INSERT INTO tbl_category_type (category_id,category_type,relative_efficency, category_type_weight)
SELECT category_id
    , "Don't Know" category_type
    , SUM(relative_efficency*marketShare)  as relative_efficency
    , 1.0 category_type_weight
FROM tbl_category_type 
group by category_id;









-- Create table bedrooms
-- 1
-- 2
-- 3 bedrooms 
-- 4
-- 5
