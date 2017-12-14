<?php

class DB_functions{
	function __construct() {
        require_once 'db_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($fname, $lname, $username, $password) {

        $stmt = $this->conn->prepare("INSERT INTO `users`(`user_name`,`first_name`,`last_name`,`pass_code`) VALUES(?, ?, ?, ?)");
        $stmt->bind_param("sssss", $fname, $lname, $username, $password);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE first_name = ? AND last_name = ?");
            $stmt->bind_param("ss", $fname, $lname);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByUsernameAndPassword($username, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM users WHERE user_name = ? AND pass_code = ?");
 
        $stmt->bind_param("ss", $username, $password);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($user_name) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE user_name = ?");
 
        $stmt->bind_param("s", $user_name);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
 
	
}
?>