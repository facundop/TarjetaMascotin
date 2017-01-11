<?php
session_cache_limiter('nocache');
header('Expires: ' . gmdate('r', 0));

header('Content-type: application/json');

// Enter your email address
$to      = 'YOUR_EMAIL_GOES_HERE';

$subject = 'Subscription Notification';

if($to) {
	$name  = 'New Subscriber';
	$email = $_POST['email'];

	$fields = array(
		0 => array(
			'text' => 'Email',
			'val' => $_POST['email']
		)
	);

	$message = "";
	$message .= "Congratulation!<br>\n";
	$message .= "You've got a new subscriber.<br>\n";
	
	foreach($fields as $field) {
		$message .= $field['text'].": " . htmlspecialchars($field['val'], ENT_QUOTES) . "<br>\n";
	}

	$headers = '';
	$headers .= 'From: ' . $name . ' <' . $email . '>' . "\r\n";
	$headers .= "Reply-To: " .  $email . "\r\n";
	$headers .= "MIME-Version: 1.0\r\n";
	$headers .= "Content-Type: text/html; charset=UTF-8\r\n";

	if (mail($to, $subject, $message, $headers)){
		$arrResult = array ('response'=>'success');
	} else{
		$arrResult = array ('response'=>'error');
	}

	echo json_encode($arrResult);

} else {

	$arrResult = array ('response'=>'error');
	echo json_encode($arrResult);

}
?>