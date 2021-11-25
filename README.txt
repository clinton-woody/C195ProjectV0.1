•  a description of the additional report of your choice you ran in part A3f


TITLE
    - C195Project

PURPOSE
    - An appointment scheduling application that interfaces with and manipulates a database allowing for the
      creation/deletion of customers and the creation/deletion of appointments.

AUTHOR
    - Clinton Evan Woody
    - Student Number: 000319162

CONTACT INFO
    - cwoody3@wgu.edu

STUDENT APPLICATION VERSION
    - Version 1.0

DATE
    - 11/22/2021

IDE
    - IntelliJ IDEA 2021.1.1 Community Edition

JDK
    - Java SE Development Kit 11.0.11

JAVAFX
    - JavaFX-SDK-11.0.2

DIRECTIONS
    - Login with
        - User Name: test
        - Password: test

    User Input Options by Screen

        - Login Screen
            - User Textbox: takes user name
            - Password Textbox: takes password
            - Login Button: Compares username/password combination against the database and if a match progresses to
                Customer Screen.  Also checks appointments for the user to see if there are any scheduled appointments
                within 15 minutes.
            - Reset Button: Resets the Login Screen
            - Time Zone Label: displays system timezone
            - Language label: displays system language


        - Customer Screen
            - Customer Table View: Pulls in all customers in the table.  Only select one customer at a time.
            - Schedule Button: Changes screen to Schedule Screen
            - New Button: Changes screen to Customer Update Form for new customers.
            - Update Button: Changes screen to Customer Update Form for updating customers.
            - Delete Button on first push: Checks selected customer for appointments and, if no
                appointments are assigned, marks it for deletion and prompts user for a second button press to confirm.
            - Delete Button on second push: Checks that the customer selected was the same customer selected during the
                first push. If the customer is the same then the customer is deleted.

        - Schedule Screen
            - Appointment Table View: Pulls in appointments to the table view based on which radio button is
                selected.  Default is monthly.  Only select one appointment at a time
                Weekly Radio Button:  If selected, displays all appointments that take place during the calendar
                    week that the system time is set to.
                Monthly Radio Button: If selected, displays all appointments that take place during the calendar
                    month that the system time is set to.
                All Radio Button: If selected, displays all appointments in the database.
            - Customer Button: Changes screen to Customer Screen.
            - Report Button: Launches a report in the text area based on which radio button is toggled.
                Customer Appointment Report: If selected, when the Report Button is pressed, run the customer report.
                Contact Schedule Report: If selected, when the Report Button is pressed, run the Contact Schedule report
                Appointment Location Report: If selected, when the Report Button is pressed, run the Appointment
                    Location report
            - New Button: Changes screen to New Appointment Form for new appointments.
            - Update Button: Changes screen to Appointmennt Update Form for updating appointments.
            - Delete Button on first push: Marks appointment for deletion and prompts user for a second button press to
                confirm.
            - Delete Button on second push: Checks that the appointment selected was the same appointment selected during
                the first push. If the appointment is the same then the appointment is deleted.

        - Customer Update Form/New Customer Form:
            - Customer ID Field: Cant interact with. Will be empty if new customer.  Will be populated from selected
                cusomer on Customer Screen if updating.
            - Name Field: Can interact with. Will be empty if new customer.  Will be populated from selected cusomer on
                Customer Screen if updating.
            - Address Field: Can interact with. Will be empty if new customer.  Will be populated from selected cusomer
                on Customer Screen if updating.
            - Postal Code Field: Can interact with. Will be empty if new customer.  Will be populated from selected
                cusomer on Customer Screen if updating.
            - Phone Number Field: Can interact with. Will be empty if new customer.  Will be populated from selected
                cusomer on Customer Screen if updating.
            - Country Combo Box: Can interact with. Will be empty if new customer.  Will be populated from selected
                cusomer on Customer Screen if updating.  This choice will determine what will be populated in the First
                Level Division Combo Box.
            - First Level Division Combo Box: Can interact with. Will be empty if new customer.  Will be populated from
                selected cusomer on Customer Screen if updating.  This box will be populated from the choice selected in
                the Country Combo Box.
            - Submit Button if new: Creates a customer based on the above inputted information.  Opens the Customer
                Screen
            - Submit Button if updating:  Updates the customer matching the customer ID in the customer ID field.  Opens
                the Customer Screen
            - Cancel Button: Opens the Customer Screen
            - Reset Button: Resets the Customer Update Form or New Customer Form to initial condition


        - Appointment Update Form/New Appointment Form:
            - Appointment ID Field: Can't interact with. Will be empty if new appointment.  Will be populated from
                selected appointment on Schedule Screen if updating.
            - Type Field: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Title Field: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Description Field: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Contact Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Customer Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from selected
            appointment on Schedule Screen if updating.
            - User Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Location Field: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Date Picker: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - Start Hour Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from
            selected appointment on Schedule Screen if updating.
            - Start Minute Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from
                selected appointment on Schedule Screen if updating.
            - End Hour Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from selected
                appointment on Schedule Screen if updating.
            - End Minute Combo Box: Can interact with. Will be empty if new appointment.  Will be populated from
            selected appointment on Schedule Screen if updating.
            - Submit Button: Opens the Schedule Screen
            - Reset Button: Resets the Appointment Update Form or New Appointment Form to initial condition

ADDITIONAL REPORTS
    - List of appointments ordered by location with Contact Name, Customer Name, and Title

MYSQL CONNECTION DRIVER VERSION
    - mysql-connector-java-8.0.25

Lambda Locations (these are alse specified in javadoc)
    - Lambda Type 1:
        - File: ScheduleScreen (lines: 203, 234, 277)
        - Purpose: Take report paramaters and output to text area.
    - Lambda 2:
        - File: AppointmentUpdateForm2 (lines: 419, 425,431, 437, 444, 451, 458, 464), CustomerUpdateForm (274, 278)
        - Purpose: Listen for a change to the selected object in a list and get a field from the newly selected object.

    //check javadocs (slide bar/private members)