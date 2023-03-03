package model;

import java.time.LocalDateTime;

/**
 * model class for appointments
 */

public class appointmentModel {

        private int appointmentID;
        private String appointmentTitle;
        private String appointmentDescription;
        private String appointmentLocation;
        private String appointmentType;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        public int customerID;
        public int userID;
        public int contactID;

        public appointmentModel(int appointmentID, String appointmentTitle, String appointmentDescription,
                            String appointmentLocation, String appointmentType, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID,
                            int userID, int contactID) {
            this.appointmentID = appointmentID;
            this.appointmentTitle = appointmentTitle;
            this.appointmentDescription = appointmentDescription;
            this.appointmentLocation = appointmentLocation;
            this.appointmentType = appointmentType;
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            this.customerID = customerID;
            this.userID = userID;
            this.contactID = contactID;
        }

        /**
         *
         *getter
         */
        public int getAppointmentID() {

            return appointmentID;
        }

        /**
         *
         * getter
         */
        public String getAppointmentTitle() {

            return appointmentTitle;
        }

        /**
         *
         * getter
         */
        public String getAppointmentDescription() {

            return appointmentDescription;
        }

        /**
         *
         * getter
         */
        public String getAppointmentLocation() {

            return appointmentLocation;
        }

        /**
         *
         * getter
         */
        public String getAppointmentType() {

            return appointmentType;
        }


        /**
         *
         * getter
         */
        public LocalDateTime getStartDateTime() {


            return startDateTime;
        }



        /**
         *
         * getter
         */
        public LocalDateTime getEndDateTime() {

            return endDateTime;
        }

        /**
         *
         * getter
         */
        public int getCustomerID () {

            return customerID;
        }

        /**
         *
         * getter
         */
        public int getUserID() {

            return userID;
        }

        /**
         *
         * getter
         */
        public int getContactID() {

            return contactID;
        }

    }



