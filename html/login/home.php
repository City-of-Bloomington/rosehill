<?php
/**
 *	Logs a user into the system using CAS
 *
 * @copyright 2006-2013 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
// If they don't have CAS configured, send them onto the application's
// internal authentication system
if (!defined('CAS')) {
	header('Location: '.BASE_URL.'/login/login.php?return_url='.$this->return_url);
	exit();
}

require_once CAS.'/CAS.php';
phpCAS::client(CAS_VERSION_2_0, CAS_SERVER, 443, CAS_URI, false);
phpCAS::setNoCasServerValidation();
phpCAS::forceAuthentication();

// at this step, the user has been authenticated by the CAS server
// and the user's login name can be read with phpCAS::getUser().

// They may be authenticated according to CAS,
// but that doesn't mean they have person record
// and even if they have a person record, they may not
// have a user account for that person record.
try {
	$user = new User(phpCAS::getUser());
	$user->startNewSession();
	header("Location: ".BASE_URL);
	exit();
}
catch (Exception $e) {
	$_SESSION['errorMessages'][] = $e;
}

$template = new Template();
$template->blocks[] = new Block('loginForm.inc');
echo $template->render();
