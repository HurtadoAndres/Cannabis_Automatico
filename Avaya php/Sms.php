<?php

include 'conexion.php';
    
         $codigo= $_POST['codigo'];
        // $codigo = "INV-5f6bd23921883";

    
          
            $statement = mysqli_prepare($conexion, "SELECT Tel FROM usuario u INNER JOIN invernadero i ON (u.Cod = i.Cod_usu) WHERE i.Cod_inver = ?");
                mysqli_stmt_bind_param($statement, 's', $codigo);
                mysqli_stmt_execute($statement);
                
                mysqli_stmt_store_result($statement);
                mysqli_stmt_bind_result($statement, $tel);
                
                $response = array();
                $response["success"] = false;  
                
                while(mysqli_stmt_fetch($statement)){
                    $response["success"] = true; 
                    $response["codigo"] = $tel;
            
                   
                }
                
       
             $telefono=$response['codigo'] ;
   $stripped = str_replace(array(',', '"'), '', $telefono);


/**
 * 
 * How to send an SMS with Zang
 * 
 * --------------------------------------------------------------------------------
 * 
 * @category  Zang Wrapper
 * @package   Zang
 * @author    Nevio Vesic <nevio@zang.io>
 * @license   http://creativecommons.org/licenses/MIT/ MIT
 * @copyright (2012) Zang, Inc. <support@zang.io>
 */


# A 36 character long AccountSid is always required. It can be described
# as the username for your account
$account_sid = 'AC777c3e32c1113e90c65647d6b7836efd';

# A 34 character long AuthToken is always required. It can be described
# as your account's password
$auth_token  = '8db790d3db9844bb9415c8470622ecf2';

# If you want the response decoded into an Array instead of an Object, set
# response_to_array to TRUE, otherwise, leave it as-is
$response_to_array = false;


# First we must import the actual Zang library
require_once '../library/Zang.php';

# Now what we need to do is instantiate the library and set the required options defined above
$zang = Zang::getInstance();



# This is the best approach to setting multiple options recursively
# Take note that you cannot set non-existing options
$zang -> setOptions(array( 
    'account_sid'       => $account_sid, 
    'auth_token'        => $auth_token,
    'response_to_array' => $response_to_array
));

# If an error occurs, Zang_Exception will be raised. Due to this,
# it's a good idea to always do try/catch blocks while querying Zang
try {
    
    # NOTICE: The code below will send a new SMS message.
    
    # Zang_Helpers::filter_e164 is a internal wrapper helper to help you work with phone numbers and their formatting
    # For more information about E.164, please visit: http://en.wikipedia.org/wiki/E.164
    
    $sms_message = $zang->create('sms_messages', array(
        'From' => '+12094350674',
        'To'   =>  $stripped ,
        'Body' => "Acabas de crear tu invernadero tu codigo es : \n$codigo "
    ));
    
    # If you wish to get back the SMS/Message SID just created then use:
    print_r($sms_message->sid);
    
    # If you wish to get back the full response object/array then use:
    print_r($sms_message->getResponse());
    
} catch (Zang_Exception $e) {
    echo "Error occured: " . $e->getMessage() . "\n";
    exit;
}