<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

if (!userIsAllowed('Deeds')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/deeds');
	exit();
}

$deed = new Deed($_REQUEST['deed_id']);
if (isset($_POST['deed'])) {
	foreach ($_POST['deed'] as $field=>$value) {
		$set = 'set'.ucfirst($field);
		$deed->$set($value);
	}

	try {
		$deed->save();
		header('Location: '.BASE_URL.'/deeds');
		exit();
	}
	catch (Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('deeds/updateDeedForm.inc',array('deed'=>$deed));
echo $template->render();