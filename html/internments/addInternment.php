<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

if (!userIsAllowed('Interments')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/interments');
	exit();
}

if (isset($_POST['interment'])) {
	$interment = new Interment();
	foreach ($_POST['interment'] as $field=>$value) {
		$set = 'set'.ucfirst($field);
		$interment->$set($value);
	}

	try {
		$interment->save();
		header('Location: '.BASE_URL.'/interments');
		exit();
	}
	catch(Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('interments/addIntermentForm.inc');
echo $template->render();