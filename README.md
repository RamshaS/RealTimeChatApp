# RealTimeChatApp
Real Time chatting application is similar with other chat applications we already have in the market. Like Whatsapp, messenger etc etc.... and is also inspired by mainly by Whatsapp. This App focused in using the feasable and very much popular technology we have in market named 'Firebase'. 

## History
This application was initiated by me in late 2017. Application was build to improve my skills in building android application also to explore the new firebase features introduced in Google i/o '17.

## Getting Started

To get started with this web portal. You need the following software to be installed before cloning this portal on your PC.

You can use any IDE. For me i am using Visual studio Code.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

To install this webportal you need to first setup Database in XAMMP the database file is located inside database folder in root directory.
After setting up Database you need to update Database username and password.
Go to jobseeker/application/config/database.php for database configuration file.

![database config file](https://static.javatpoint.com/codeigniter/images/database1.png)

In database.php file, fill the entries to connect CodeIgniter folder to your database.

![database info](https://static.javatpoint.com/codeigniter/images/database2.png)

Now change path mention in config file of codeigniter go to jobseeker/application/config/config.php
Alter the following IP as per your need.
```
$config['base_url'] = 'http://your_ip/CareerPortal/jobseeker/';

```
All setup now open browser and start using the portal.

## Deployment

For Deployment must modify database.php file inside config file. 
```
Inside $db['default'] array change 

'db_debug' => (ENVIRONMENT !== 'development')

                    to 

'db_debug' => (ENVIRONMENT !== 'production')
```
## Built With

* [Codeigniter3 - PHP](https://codeigniter.com/user_guide/index.html) - The web framework used
* [Bootstrap](https://getbootstrap.com/) - For User Interface
* [JQUERY](https://jquery.com/) - For validation, actions and animations
* [MYSQL](https://dev.mysql.com/doc/) - Database
* [XAMPP](https://www.apachefriends.org/index.html) - Server used
* [CAPTCHA](https://www.google.com/recaptcha/intro/v3.html) - Captcha to stop bots to fill forms


## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/RamshaS/CareerPortal) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Ramsha Saeed** - *Initial work* - [RamshaS](https://www.linkedin.com/in/ramsha-saeed/)

See also the list of [contributors](https://github.com/RamshaS/CareerPortal/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
