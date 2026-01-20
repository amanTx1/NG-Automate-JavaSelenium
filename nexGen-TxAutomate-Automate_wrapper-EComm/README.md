<h3>To log defects in JIRA</h3>
<li>Execute the commands in lib folder using CMD mentioned in ExternalMavenJars.txt file which is kept inside lib folder.</li>
<h3>For generating the code</h3>
<li>mvn exec:java</li>
<h3>For executing TCs through commandline</h3>
<li></li>
<h3>For executing TCs through VSCODE</h3mvn test -Dcucumber.filter.tags="@TagName">
<li>mvn test '-Dcucumber.filter.tags="@TagName"'</li>
<p>Project requires JDK following verison to execute</p>
<h3>17</h3>
<p>Use SonarQube atleast at following version if required</p>
<h3>10</h3>
<p>Use Apache Jmeter atleast at following version if required</p>
<h3>5.5</h3>
<p>Use Jenkins atleast at following version if required</p>
<h3>2.401.1 LTS</h3>
<p>To launch Jenkins use following command as it is suppose to run on JDK atleast 17</p>
<h3> java -jar jenkins.war --enable-future-java</h3>
<p>Make sure to do changes in SonarQube as administrator to add your project to it</p>
<p>Make sure to do following changes in AutomationControlSheet.xlsx before execution of test cases on Browserstack</p>
<li>Add browserstack username</li>
<li>Add browserstack key</li>
<p>Make sure to do following changes in AutomationControlSheet.xlsx before execution of mobile test cases on Browserstack</p>
<li>Add browserstack username</li>
<li>Add browserstack key</li>
<li>Add device ID</li>
<p>Make sure to do following changes in Browserstack before execution of mobile test cases on Browserstack</p>
<li>Upload your application</li>
<p><b>Following are the highlighted version being used in the framework</b></p>
<li>Selenium 3</li>
<li>Appium 7</li>
<li>Rest Assured 5</li>
<li>Extent report 5</li>
<li>Log4j 2</li>
<li>Cucumber 7</li>
