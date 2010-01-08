<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
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

$template = new Template('full-width');
$template->blocks[] = new Block('interments/maps.inc',array('interment'=>$interment));
echo $template->render();