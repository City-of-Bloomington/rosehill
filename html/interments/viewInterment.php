<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param GET interment_id
 */
try {
	if (!isset($_GET['interment_id']) || !$_GET['interment_id']) {
		throw new Exception('interments/unknownInterment');
	}
	$interment = new Interment($_GET['interment_id']);
}
catch (Exception $e) {
	$_SESSION['errorMessages'][] = $e;
	header('Location: '.BASE_URL.'/interments');
	exit();
}


$template = new Template();
$template->blocks[] = new Block('interments/intermentInfo.inc',array('interment'=>$interment));
$template->blocks['panel-one'][] = new Block('interments/mapLinks.inc',
											array('interment'=>$interment));
echo $template->render();