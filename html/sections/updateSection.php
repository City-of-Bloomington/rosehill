<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param GET section_id
 */
if (!userIsAllowed('Sections')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/sections');
	exit();
}

$section = new Section($_REQUEST['section_id']);
if (isset($_POST['section'])) {
	$section->setCode($_POST['code']);
	$section->setName($_POST['name']);

	try {
		$section->save();
		header('Location: '.$section->getCemetery()->getURL());
		exit();
	}
	catch (Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('sections/updateSectionForm.inc',array('section'=>$section));
echo $template->render();