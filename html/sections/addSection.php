<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param REQUEST cemetery_id
 */

if (!userIsAllowed('Sections')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/sections');
	exit();
}

$cemetery = new Cemetery($_REQUEST['cemetery_id']);

if (isset($_POST['code'])) {
	$section = new Section();
	$section->setCemetery($cemetery);
	$section->setCode($_POST['code']);
	$section->setName($_POST['name']);

	try {
		$section->save();
		if (isset($_FILES)) {
			if (isset($_FILES['highlight_map']) && $_FILES['highlight_map']['tmp_name']) {
				$section->saveMap($_FILES['highlight_map'],'highlight');
			}
			if (isset($_FILES['zoom_map']) && $_FILES['zoom_map']['tmp_name']) {
				$section->saveMap($_FILES['zoom_map'],'zoom');
			}
		}
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