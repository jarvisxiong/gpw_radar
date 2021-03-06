# Architecture overview
![alt tag](https://github.com/MateuszPu/gpw_radar/blob/dev/architecture.png)

# gpwRadar
This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

Before you can build this project, you must install and configure the following dependencies on your machine:

[Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system (Only if you want to use grunt, without it project works correctly). Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

As the all front-end dependencies are not included on this repository. You have to install bower, go to catalog ../projectPath/gpw_radar, and run bower install command.
All dependencies should be downloaded by bower.

Project use postgersql database. You should have working client of postgres. By default the name of the database is: `gpw_radar`, login and password: `postgres`.
If you want to change the default setting go to: `/gpw_radar/src/main/resources/config` and edit `application-dev.yml` or `application-prod.yml` config file.

# Building for production

To optimize the gpwRadar client for production (here you will need grunt), run:

    mvn -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

# Run from IDE

If you want to run project from IDE (Intellij in my case), you should navigate to gpw-radar module and run main method from Application class.
Before run you should edit run configuration: in `working directory` you should provide `path to the gpw-radar module`, instead of that application will be not working correctly.

# Issue/task tracker
Under following link (https://trello.com/b/P8ASjAks/gpw-radar) I manage my work on the project.

# Rabbitmq on Docker
In this particular project I am using rabbitmq. It is configured in docker container [Docker][].

 For person who is familiar with docker:

 1) Go to the folder `configs/rabbitmq/docker`

 2) Particular folder contains dockerfile which you should use to build rabbitmq docker image

 3) Use following command to build image:  `docker build -t name:version path to dockerfile`

 4) Run the image with following command: `docker run -p 5672:5672 -p 15672:15672 name:version`

 5) To verify if everything works go to the http://docker_ip_machine:15672 and login with admin admin

 For person who is not familiar with docker:

 1) Go to the [Rabbitmq][] and install rabitmq locally.

 2) Go to the folder `configs/rabbitmq/docker`

 3) There is file `init.sh`

 4) run it

 5) To verify if everything works go to the http://docker_ip_machine:15672 and login with admin admin

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Docker]: https://docs.docker.com/
[Rabbitmq]: http://www.rabbitmq.com/download.html
