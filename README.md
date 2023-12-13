# Hospital Management System

This is a Java-based Command Line Interface (CLI) application designed for managing hospital operations. It leverages JDBC (Java Database Connectivity) to interact with a database.

## Features

- **Patient Management:** Add, update, and remove patient records.
- **Doctor Management:** Maintain details of doctors in the hospital.
- **Appointment Handling:** Schedule and manage patient appointments.
- **Prescription Management:** Record and manage prescriptions for patients.
- **Billing:** Handle billing and payment information.

## Getting Started

To run this application, make sure you have Java installed on your system. Additionally, set up a relational database and configure the JDBC connection settings in the code.

## Usage

1. **Patient Management:**
   - Add a new patient: `java HospitalManager addPatient`
   - Update patient details: `java HospitalManager updatePatient`
   - Remove a patient: `java HospitalManager removePatient`

2. **Doctor Management:**
   - Add a new doctor: `java HospitalManager addDoctor`
   - Update doctor details: `java HospitalManager updateDoctor`
   - Remove a doctor: `java HospitalManager removeDoctor`

3. **Appointment Handling:**
   - Schedule an appointment: `java HospitalManager scheduleAppointment`
   - Manage appointments: `java HospitalManager manageAppointments`

4. **Prescription Management:**
   - Add a prescription: `java HospitalManager addPrescription`
   - View prescriptions: `java HospitalManager viewPrescriptions`

5. **Billing:**
   - Generate bill: `java HospitalManager generateBill`
   - Process payments: `java HospitalManager processPayment`

## Database Configuration

The application requires a configured JDBC connection to a database. Update the connection details in the code to connect to your database.

