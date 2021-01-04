# RESTful web service

Retrieve all users - GET /users </br>
Create a user - POST /users </br>
Retrieve one user - GET /users/{uid} -> /users/1 </br>
Delete a user - DELETE /users/{uid} -> /users/1 </br>

Retrieve all posts for a user -> GET /users/{uid}/posts </br>
Create a posts for a user -> POST /users/{uid}/posts </br>
Retrieve detail of a post -> GET /users/{uid}/posts/{pid} </br>


#### Internationalization - Customizing service for international users
##### Configuration
        - LocaleResolver
            - Default Locale - Locale.US
        - ResouceBundleMessageSource
        
#### Content Negotiation
XML Enabled.
Browsers give preference to XML over JSON (They send Accept header with application/xml). 