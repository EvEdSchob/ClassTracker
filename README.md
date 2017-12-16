# ClassTracker
## 12-15-17
## Author

Evan Schober

## Description

This application provides basic functionality for maintaining a list of courses, the assignments done for each course, the grades for each of the assignments as well as the raw cumulative score for each class. 

The app opens to a list of all of the currently stored courses. At first launch this list will be populated with a few procedurally generated courses and a number of assignments. These courses may be deleted but will not be regenerated as long as the app's cache remains intact.

New courses may be added from the "Add Course" button in upper right hand corner of the display.
Long pressing on any course will open the details for that course and allow it to be modified.

A short press on any course will open the list of assignments for the given course.
Similar rules apply for the addition and modification of assignments. The only difference being that, due to the lack of possible actions that may be completed on an assignment there was no need to implement a long press for the assignments. Simply touching any assignment will open its edit window.

Any modifications to any list (assignments or courses) causes all app data to be saved for restoration on next launch. Data storage and modification happen separately, reducing the likelihood of data corruption. The course list maintains the "live" data while the app is running, storing all courses and assignments as POJOs.
The app's cold storage is done using JSON, converted to/from POJOs using the GSON serialization/ de-serialization library. The android PreferenceManager maintains the storage of this JSON string between sessions.

##Notes, Problems And/or Errors

In its current state the app does not have the ability to calculate course grade based on a weighted scale. All assignments within a course are graded equally.
It does not have the ability to handle grading scales other than 4.0.
There presently is no functionality to account for variances in percentage score vs grade point earned.
E.g. The minimum score for a 4.0 in a given course may be 90% as where a professor that grades on a harder scale may say that a 4.0 starts at 93%. 
