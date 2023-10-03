# Mini Project 
## Software Engineering - CS305

#### <pre> Name: Tanuj Kumar    Entry No: 2020csb1134 </pre>

### About the project:
In order to manage the academics at IIT Ropar,
we should design and implement a multi-user database application. We would be concentrating on developing a 
command line interface to communicate 
with the database for the sake of ease. 
The available functionalities are based on the academic regulations of our institution.


### How to compile:
- Download the zipped folder and unzip it .
- Run the database file.
- Connect the java code with your database server with your respective username and password.
- Open the terminal and write to build the gradle, This will also generate the jacoco test report .

    <b> "./gradlew clean build jacocotestreport"</b>
- After that to run the code , either write 

    <b>"./gradlew run""</b>

    Or click on the respective run button of your IDE.


### Project Features

#### Student Features:
- Registration and deregistration for courses:
   - Verify that the student has cleared the pre-requisites before allowing registration
   - Check the allowed credit limit for each student
- View their grades for each course
- Calculate their current CGPA

#### Academic Office Features:
- Edit the course catalog with a username "Staff Dean's office"
- View grades of all students
- Generate transcripts of students in a .txt file format

#### Faculty Features:
- View grades of all students enrolled in their courses
- Register and deregister courses they wish to offer
- Update course grades through .csv files. The system accepts a file path as input, and updates the database accordingly.


### Assumptions:

- The Jacoco Test report i.e the test case submitted along with is only for functionalities rather than for both UI and functionality , because UI code is for input entire logic code is in the db code files.
- Users in the database will be added manually by the admin as there is no reading of .csv files recommended for this.
- Initial password for each user is same i.e 'iitrpr' although user can change it .


## Thank You

Thank you for checking out my project! I hope you found it interesting and useful.

