<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param REQUEST return_url
 */
if (!userIsAllowed('Interments')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/interments');
	exit();
}

$return_url = isset($_REQUEST['return_url']) ? $_REQUEST['return_url'] : BASE_URL.'/interments';

$interment = new Interment($_REQUEST['interment_id']);
if (isset($_POST['interment'])) {
	foreach ($_POST['interment'] as $field=>$value) {
		$set = 'set'.ucfirst($field);
		$interment->$set($value);
	}

	try {
		$interment->save();
		header("Location: $return_url");
		exit();
	}
	catch (Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('interments/updateIntermentForm.inc',
								array('interment'=>$interment,'return_url'=>$return_url));
echo $template->render();