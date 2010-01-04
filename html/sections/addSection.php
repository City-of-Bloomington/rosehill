<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param REQUEST cemetery_id
 */

if (!userIsAllowed('Sections')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/sections');
	exit();
}

$cemetery = new Cemetery($_REQUEST['cemetery_id']);

if (isset($_POST['section'])) {
	$section = new Section();
	$section->setCemetery($cemetery);
	$section->setCode($_POST['code']);
	$section->setName($_POST['name']);

	try {
		$section->save();
		header('Location: '.$cemetery->getURL());
		exit();
	}
	catch (Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('sections/addSectionForm.inc',array('cemetery'=>$cemetery));
echo $template->render();