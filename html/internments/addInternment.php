<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

if (!userIsAllowed('Internments')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/internments');
	exit();
}

if (isset($_POST['internment'])) {
	$internment = new Internment();
	foreach ($_POST['internment'] as $field=>$value) {
		$set = 'set'.ucfirst($field);
		$internment->$set($value);
	}

	try {
		$internment->save();
		header('Location: '.BASE_URL.'/internments');
		exit();
	}
	catch(Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('internments/addInternmentForm.inc');
echo $template->render();