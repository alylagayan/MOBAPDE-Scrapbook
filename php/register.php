<?php
 
require_once 'db_function.php';
$db = new DB_functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['f_name']) && isset($_POST['l_name']) && isset($_POST['u_name']) && isset($_POST['password'])) {
 
    // receiving the post params
    $fname = $_POST['f_name'];
	$lname = $_POST['l_name'];
    $uname = $_POST['u_name'];
    $password = $_POST['password'];
 
    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["user"]["first_name"] = $user["name"];
			$response["user"]["last_name"] = $user["name"];
            $response["user"]["last_name"] = $user["email"];

            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>