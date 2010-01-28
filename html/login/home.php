<?php
/**
 *	Logs a user into the system.
 *
 *	A logged in user will have a $_SESSION['USER']
 *								$_SESSION['IP_ADDRESS']
 *
 * @copyright 2006-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
if (isset($_REQUEST['return_url'])) {
	$_SESSION['return_url'] = $_REQUEST['return_url'];
}
require_once '/var/www/libraries/SimpleCAS/SimpleCAS/Autoload.php';

$options = array('hostname'=>'bandit.bloomington.in.gov',
				'uri'=>'cas');
$protocol = new SimpleCAS_Protocol_Version2($options);

$client = SimpleCAS::client($protocol);
$client->forceAuthentication();

if ($client->isAuthenticated()) {
	$user = new User($client->getUsername());
	$user->startNewSession();
	setcookie('cas_session','true',0,'/','.bloomington.in.gov');

	if (isset($_SESSION['return_url'])) {
		header('Location: '.$_SESSION['return_url']);
	}
	else {
		header('Location: '.BASE_URL);
	}
}
else {
	echo "Could not log in";
}
