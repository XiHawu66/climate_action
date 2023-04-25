CREATE DATABASE IF NOT EXISTS carbonique;

use carbonique;


/************************** UNITS ****************************************/
drop table if exists tbl_unit_conversion;

create table IF NOT EXISTS tbl_unit_conversion
(
    fuel varchar(50) primary key
    ,units varchar(3)
    ,co2_kg float
    ,unit_cost float
    ,unit_sell float default 0
    ,kwh_per_unit float
);


INSERT INTO tbl_unit_conversion 
     (units,fuel            , co2_kg , unit_cost, unit_sell , kwh_per_unit) VALUES
     ('kWh','electricity'   , 0.47300, 0.2333   ,  0.055    , 1.0)
    ,('MJ' ,'gas'           , 0.05553, 0.0431   ,  0.000    , 1/3.6) 
    ,('MJ' ,'lpg'           , 0.06420, 0.0800   ,  0.000    , 1/3.6) /*lpg 2200MJ in a standard 45kg gas bottle at $177.*/
;

/************************** houshold size (people) ***************************/
drop table if exists tbl_household;

Create table IF NOT EXISTS tbl_household 
(
     people int PRIMARY KEY
   , house_hold_size_label varchar(3)
   , kwh_per_year float
   , sugested_tank_size CHAR(8)
   , sugested_fridge_size CHAR(8)
);

INSERT INTO tbl_household 
    (
        people, 
        house_hold_size_label, 
        kwh_per_year, 
        sugested_tank_size,
        sugested_fridge_size
    ) 
    values 
         (1, '1' , 2954, '115-150L', '200-350L')
        ,(2, '2' , 4840, '115-150L', '300-400L')
        ,(3, '3' , 5077, '150-190L', '350-500L')
        ,(4, '4' , 5805, '190-230L', '450-600L')
        ,(5, '5+', 7351, '230-300L', '600-800L')
;

/************************** home size (area) ********************************/
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
   , area_square_metres float       
);
   
/*
https://ahd.csiro.au/dashboards/energy-rating/ncc-climates/
Existing dewellings in climate zone 6
Heating 419.74 MJ per M squared; 1kw = 3.6 mj
Cooling  44.53 MJ per M squared
*/
INSERT INTO tbl_bedrooms (bedrooms, bedroom_label, area_square_metres) values 
     (1, '1' , 1*60 )
    ,(2, '2' , 2*60 )
    ,(3, '3' , 3*60 )
    ,(4, '4' , 4*60 )
    ,(5, '5+', 5*60 )
;

drop table if exists tbl_bedrooms_category;
create table if not exists tbl_bedrooms_category
(
     bedrooms int 
    ,category_id int 
    ,kwh_per_year float
    ,CONSTRAINT pk_bedrooms_category PRIMARY KEY (bedrooms,category_id)
);

/* kwhper year = area_square_metres * mj_per_square_metre/3.6  */
insert into tbl_bedrooms_category
SELECT 
     bedrooms
    ,1 heating
    ,area_square_metres * 419.74 / 3.6 as kwh_per_year
from tbl_bedrooms
UNION
SELECT 
     bedrooms
    ,6 cooling
    ,area_square_metres * 44.53 / 3.6 as kwh_per_year
from tbl_bedrooms;



/************************** CATEGORIES ********************************/

DROP table IF EXISTS tbl_category;

Create table IF NOT EXISTS tbl_category
(
     category_id int NOT NULL Primary key
    ,category varchar(255) NOT NULL
    ,category_weight float NOT NULL
);


INSERT INTO tbl_category 
     (category_id, category         , category_weight) values 
     (1          , 'Heating'        , .38) 
    ,(2          , 'Hot Water'      , .15)
    ,(3          , 'Refrigeration'  , .11)
    ,(4          , 'lighting'       , .06)
    ,(7          , 'Clothes Dryer'  , .05)
;


INSERT INTO tbl_category 
    (category_id,category,category_weight)
SELECT 8, 'Other', round(1- SUM(category_weight),3)
FROM tbl_category;


/************************** SUB CATEGORIES ********************************/

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


/************************** SUB CATEGORIES - LIGHTING ***************************/

-- LIGHTING
INSERT INTO tbl_category_type (category_id,category_type,relative_efficency,market_share) VALUES
     (4,'Incandescent'  , 1.00  , 0.1 )
    ,(4,'Halogen'       , 0.7   , 0.2 )
    ,(4,'CFL'           , 0.22  , 0.5 )
    ,(4,'LED'           , 0.17  , 0.2 )
;



/************************** SUB CATEGORIES - HEATING ***************************/
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


/************************** SUB CATEGORIES - HOT WATER ***************************/
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



/************************** SUB CATEGORIES - Refrigeration ***************************/
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

/*
Source: https://reg.energyrating.gov.au/comparator/product_types/35/search/?expired_products=on&page=3
  none or done use  |0 per load         | 57%        
  Vented            |7.0 kwh per load   | 15% (condenser 1-4.5 stars)
  Condenser         |4.8 kwh per load   | 16% (condenser 6-10 stars)
  Heat pump         |1.87 kwh per load  | 10%

kw per load = relative_efficency
kw per year = 52* [loads per week] * relative_efficency 
*/

INSERT INTO tbl_category_type (category_id,category_type,relative_efficency,market_share) VALUES
     (7,'Vented'      , 7.0  , .15)
    ,(7,'Condenser'   , 4.8  , 0.16 ) 
    ,(7,'Heat Pump'   , 2.0  , 0.10 ) 
;


/************************** SUB CATEGORIES - WEIGHT NORMALISATION ********************/
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


/************************** SUB CATEGORIES - DEFAULT OPTION (Dont Know) *****************/
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



/************************** SUB CATEGORIES - Saving Potential *****************/
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




/************************** SOLAR  *****************/

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

/*
Cost of panels: 
https://www.cleanenergycouncil.org.au/consumers/buying-solar/costs-and-savings
*/

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

/**********************SOLAR USAGE AND GENERATION CURVES****************************************/
/*
hourly_generation: (energy generation profile)
is a normal distribution centered at midday with a standard deviation of 3 hours
when multipled by the sun_hours_per_day it represents the expected generation curve of a 1kw system
The users daily generation curve by hour is product of hourly_generation * sun_hours_per_day * kw_system_size 

hourly_usage: (energy usage profile)
it is an anergy usage profile expressed as % of daily usage for each ower of the day
the users daily usage curve by hour is the product of hourly_usage * [average daily total usage]
the hourly_usage : 
ESSENTIAL SERVICES COMMISSION (2019)
https://www.esc.vic.gov.au/sites/default/files/documents/victorian-energy-usage-profiles-report.pdf
VICTORIAN ENERGY USAGE PROFILES PROFILE CALCULATION METHODOLOGY AND RESULTS
FIGURE 4.3 CONSUMPTION PROFILES BY CLIMATE ZONE (climate Zone 6)

Calculations
self_consumption kwh is the lesser of the hourly_usage and hourly_generation
exported kwh = hourly_generation - self_consumption
*/

DROP TABLE IF EXISTS tbl_solar_day;

create table IF NOT EXISTS tbl_solar_day 
(
     hour int PRIMARY KEY
    ,hourly_usage float 
    ,hourly_generation float 
);


INSERT INTO tbl_solar_day (hour, hourly_usage,hourly_generation)
VALUES 
 ( 1		,0.034		,0.000)
,( 2		,0.030		,0.000)
,( 3		,0.023		,0.000)
,( 4		,0.027		,0.000) 
,( 5		,0.030		,0.005) 
,( 6		,0.038		,0.019)
,( 7		,0.038		,0.034)  
,( 8		,0.039		,0.056)
,( 9		,0.038		,0.082)
,(10		,0.030		,0.108)
,(11		,0.030		,0.128)
,(12		,0.030		,0.136)
,(13		,0.030		,0.128)
,(14		,0.038		,0.108)
,(15		,0.045		,0.082)
,(16		,0.060		,0.056)
,(17		,0.076		,0.034)
,(18		,0.076		,0.019)
,(19		,0.076		,0.005)
,(20		,0.076		,0.000)
,(21		,0.060		,0.000)
,(22		,0.045		,0.000)
,(23		,0.038 		,0.000)
,(24		,0.038		,0.000)
;

/*******************************USER PERMISSIONS**********************************/

DROP USER IF EXISTS 'spring'@'localhost';
CREATE USER IF NOT EXISTS 'spring'@'localhost' IDENTIFIED BY '4tsZreage1jNz6wn2';
GRANT SELECT ON carbonique.* TO 'spring'@'localhost';
FLUSH PRIVILEGES;




/*******************************EXAMPLE QUERIES**********************************/

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
    AND c.category_id <> 7
    UNION 
    select 
            c.category
            , ct.category_type
            , u.units 
            , ROUND(
                3 /*loads per week example 2*/
                * relative_efficency /*kwh per load*/
                * 52 /*weeks per year*/
                ,0)
            as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_category_type ct
    inner join tbl_category c on ct.category_id = c.category_id
    inner join tbl_unit_conversion u on ct.fuel = u.fuel
    where c.category_id = 7 /*laundry*/    
    UNION 
    /*HOT WATER*/
    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round((bc.kwh_per_year /u.kwh_per_unit)  * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from 
        tbl_category c
        inner join tbl_bedrooms_category bc on c.category_id = bc.category_id
        inner join tbl_category_type ct on bc.category_id = ct.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE bc.bedrooms = 3;    
    ;    
    /*AND ct.category_type_id in (1, 16, 7 , 22, 8)*/



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

/********************** HOT water Recomendation ********************************/
/*
IF [reduction_potential] = 0
    Your [current category_type] is very efficient. When your system is ready for replacement recomend a eigher a Heat Pump.
ELSE 
    Moving from your current [current category_type] hot water system to a Heat Pump could reduce your annual CO2 by [co2_kg_reduction]. 

    With grid consumption you could save $[grid_gridsaving] per year. Heat pumps are a great in combination with solar.
    With solar the savings could be $[solar_saving]  based on an export price of [export_price*100]c per kwh.

    If they have solar:
        Because you already have solar you can increase your self consumption . 

A tank size of [sugested_tank_size] is recomended for a houshold of [people] [person/people]

If you have the roof space you might also consider an Electric boosted system. However if roof space is tight is often more sensible to add additional solar panels. 

IF  [current_fuel] == gas 
    Once all your gas appliances are removed. Disconecting your gas will save between $250 and $360 per year.

IF there current [current_fuel] == lpg 
    Once all your gas appliances are removed. Disconecting your gas eliminates delivery fees. 
*/


-- Example HOT water Recomendation
WITH savings as 
(
    SELECT 
          c.category
        , ct.category_type
        , u.units 
        , round(h.kwh_per_year * c.category_weight * ct.category_type_weight,2)  as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , u.unit_sell
        , ct.reduction_potential
        , u.fuel
        , h.sugested_tank_size
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND ct.category_type_id = 14
)
select 
    
     s.category_type as current_category_type
    ,s.fuel current_fuel
    ,s.sugested_tank_size
    ,s.qty * s.co2_kg_per_unit * s.reduction_potential as co2_kg_reduction
    ,s.qty * s.reduction_potential * s.unit_cost as grid_grid_saving
    ,e.unit_sell export_price
    /*grid_grid_saving * (e.unit_sell / e.unit_sell)*/
    ,(s.qty * s.reduction_potential * s.unit_cost) * (e.unit_cost/ e.unit_sell) as solar_saving
from savings s, tbl_unit_conversion as e
where e.units = 'kwh';


/********************** LIGHTING Recomendation ********************************/
/*
IF [reduction_potential] = 0
    Your [current_category_type] installation is very efficient. When it comes time for replacement we recomend LEDs.
ELSE 
    Moving from your current lighting of [current_category_type] to a LED could reduce your annual [reduction_potential*100]% preventing [co2_kg_reduction] Kg of CO2 each year. 
    With grid consumption you could save $[grid_saving].

static content - expand on this
-LEDs rated xxxx allow you to run Insulation right over the top with no gaps. 
-Cost of LED 
-Vic gov rebates
/****************************************************************************/

-- Example LIGHTING recomendations 
    SELECT 
          c.category
        , ct.category_type current_category_type
        , h.kwh_per_year 
            * c.category_weight 
            * ct.category_type_weight \
            * u.unit_cost 
         as grid_saving
        , h.kwh_per_year 
            * c.category_weight 
            * ct.category_type_weight 
            * ct.reduction_potential 
            * u.co2_kg 
         as co2_kg_reduction
        , ct.reduction_potential
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND ct.category_type_id = 1
    ;


/******************** Refrigeration *******************************************8/
IF [reduction_potential] = 0
    Running 1 fridge is an excellent choice. 
ELSE 
    It’s more efficient to run one larger fridge/freezer than multiple smaller ones. Running a single fridge would reduce your costs by [reduction_potential*100]% and prevents [co2_kg_reduction] Kg of CO2 each year. 
    With grid consumption you could save $[grid_saving]. It could be even more than. Fridges running in a garage consume far more in the summer heat.

Larger fridges cost more to run. A fridge of [sugested_fridge_size]itres is recomended for a houshold of [people] [person/people]

Fridges pull small constant loads making them excellent at increasing self consumption of solar for 50% of the day.

static content - expand on this
It's also worth considering the energy star rating of your fridge. A 4 star rating is nearly twice as efficent as a 2 star rating fridge. For a typical [sugested_fridge_size] fridge that is a $70 savings per year.

/*******************************************************************************/

    SELECT 
          c.category
        , ct.category_type current_category_type
        , h.kwh_per_year 
            * c.category_weight 
            * ct.category_type_weight \
            * u.unit_cost 
         as grid_saving
        , h.kwh_per_year 
            * c.category_weight 
            * ct.category_type_weight 
            * ct.reduction_potential 
            * u.co2_kg 
         as co2_kg_reduction
        , ct.reduction_potential
        ,h.sugested_fridge_size
    from tbl_household h,
        tbl_category_type ct
        inner join tbl_category c on ct.category_id = c.category_id
        inner join tbl_unit_conversion u on ct.fuel = u.fuel
    WHERE h.people = 3 
    AND ct.category_type_id = 22-- 2 fridges
    ;

/* General cat suguestion 
Moving from your current [current category_type] [category] to a [proposed category_type] system could reduce CO2 by [] and save up to $[] each year.
*/




/******************** Clothes Dryer *******************************************8/
IF [reduction_potential] = 0
    Your Heat Pump installation is the most efficient type of Dryer. Heat pump dryers range between 6 and 10 star ratings. When looking to replace your dryer, look fo a higher star rating.
ELSE 
    Moving from your current [current_category_type] Clothes Dryer to a Heat Pump Dryer could reduce your annual [reduction_potential*100]% preventing [qty * co2_kg_per_unit] Kg of CO2 each year. 
    With grid consumption you could save $[unit_cost * qty].

53% of Melboune households either dont have a dryer or use it less than once per week. Air trying your clothes is alway the cheapest option.

/*******************************************************************************/
    select 
            c.category
            , ct.category_type as current_category_type
            , u.units 
            , ROUND(
                3 /*loads per week example 2*/
                * relative_efficency /*kwh per load*/
                * 52 /*weeks per year*/
                ,0)
            as qty
        , u.co2_kg co2_kg_per_unit
        , u.unit_cost
        , ct.reduction_potential
    from tbl_category_type ct
    inner join tbl_category c on ct.category_id = c.category_id
    inner join tbl_unit_conversion u on ct.fuel = u.fuel
    where ct.category_type_id = 24 ;



/* SCRAP


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