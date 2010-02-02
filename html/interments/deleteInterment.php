<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param GET interment_id
 * @param GET return_url
 */
if (!userIsAllowed('Interments')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL);
	exit();
}

$interment = new Interment($_GET['interment_id']);
$interment->delete();

header('Location: '.$_GET['return_url']);
