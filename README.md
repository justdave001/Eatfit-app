# Eatfit
 Original App Design Project - README Template
===

Eatfit

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description


### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Food/money & Health
- **Mobile:** Andorid mobile experience
- **Story:** Allows users find required food options for their body goals in restaurants near them.
- **Market:** Anyone looking to achieve a specific body or money goal (lose weight, gain weight or save money) can use the app and past saved food options are used to enable easy and less many future choosings.
- **Habit:** Users can open the search tab and view all available restaurants around them, click on pin on those restaurants and get tailored food items based on their needs. Their chosen options are also saved to enable easier future experience.
- **Scope:** First stage would involve just being able to access restaurant items around you, and choose items based on body goal, second stage would be to tailor user xperience, based on previous chosen food options, to get an easier options menu to choose from. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* user can login
* user can create an account
* user can search for nearby restaurants
* user can select goal based on chosen restaurant (lose weight, gain weight & save money)
* user can favorite a restaurant

**Optional Nice-to-have Stories**

* user can get item recommendations
* user can order individual items
* user can undo deleted/saved items

### 2. Screen Archetypes

* Login screen
   * user can login by filling in name/username and password or by linking Facebook/Google account
  
* Registration screen
   * user can create a new account

* Home feed
   * user has view a feed of previously visited restaurants and items ordered when clicked (items as a modal overlay).

* Search
   * user can search for nearby restaurants.
   * user can click on a restaurant and view best food items based on body/money goal selection

* Profile
   * user can edit profile details (name, password, display first and last nane)
   
* Detail 
   *user can choose goal (body/money goal selection)
 
*Goal
   *user views a list of menu items with ordered list of their goals (e.g sees max calorie/protein/fat content on click of gain weight button)
   *user can add items in recyclerview to cart and checkout items
 
*Place Order Screen
   *user can view a recyclerveiw of items in cart (showing image, name,price and quantity) as well as the total cost and place order

*Favorites screen
   *user can view a list of favorited items


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Search
* Account

**Flow Navigation** (Screen to Screen)


* Login Screen
   => Home
   
* Registration Screen
   => Search
   
* Search
   => Google map showing nearby restaurants
  
* Restaurant 
   => Body/money goal options
  
* Restaurant
     => Tailored menu items
     
* Profile
     => profile info change and save and favorites icon
     


## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://drive.google.com/file/d/17oDSWXWHtBaJ8YpH8pYi6cjQSJaQajoE/view?usp=sharing" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
 **Name** String
 **Favorite restaurants count** Integer
 **password** String
 **email** varchar
 **location** String
 **Restaurant name** String
 **menu items** String
 
### Models
### Models

**User**
| property  |  type | description|
| -------- | -------- | -------- |
| object_id     | int     | unique obj id for each user    |
| f_name    | String     | user first name    |
| l_name     | String     | user last name   |
| email     | String     | user email    |
| favorites     | Array     | user favorites    |
| password     | String     | user password   |
| login_method     | bool     | either through google/facebook or app login  method   |


**Restaurant**
| property | type | description |
| -------- | -------- | -------- |
| restaurant_id |    int  | unique id for each restaurant     |
| menu_items |    Array  | List of all menu items offered by restaurant    |
|res_name |    String  | name of restaurant    |
|Timestamp |    datetime  | data and time item was ordered by user    |



### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
