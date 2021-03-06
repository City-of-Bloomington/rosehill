<?php
/**
 *	Logs a user into the system using this application's internal authentication
 *
 * @copyright 2006-2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
try {
	$user = new User($_POST['username']);

	if ($user->authenticate($_POST['password'])) {
		$user->startNewSession();
	}
	else {
		throw new Exception('wrongPassword');
	}
}
catch (Exception $e) {
	$_SESSION['errorMessages'][] = $e;
	header('Location: '.BASE_URL);
	exit();
}

// The user has successfully logged in.  Redirect them wherever you like
if ($_POST['return_url']) {
	header('Location: '.$_POST['return_url']);
}
else {
	header('Location: '.BASE_URL);
}
