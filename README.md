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
* user can favorite a restaurant

**Optional Nice-to-have Stories**

* user can get best item recommendations


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
   * user can edit profile details (name, password)
   
* Body/money goal




### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home
* Search
* profile

**Flow Navigation** (Screen to Screen)


* Login Screen
   => Home
   
   
* Registration Screen
   => Home
   
 
* Search
   => Restaurants
  

* Restaurant 
   => Body/money goal options
  
* Restaurant
     => Tailored menu items
* Profile
     => profile info change and save.


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


### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
