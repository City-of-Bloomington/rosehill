<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
if (!userIsAllowed('Cemeteries')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL.'/cemeteries');
	exit();
}

if (isset($_POST['cemetery'])) {
	$cemetery = new Cemetery();
	foreach ($_POST['cemetery'] as $field=>$value) {
		$set = 'set'.ucfirst($field);
		$cemetery->$set($value);
	}

	try {
		$cemetery->save();
		if (isset($_FILES)) {
			if (isset($_FILES['map']) && $_FILES['map']['tmp_name']) {
				$cemetery->saveMap($_FILES['map'],'full');
			}
			if (isset($_FILES['thumbnail']) && $_FILES['thumbnail']['tmp_name']) {
				$section->saveMap($_FILES['thumbnail'],'thumbnail');
			}
		}
		header('Location: '.BASE_URL.'/cemeteries');
		exit();
	}
	catch(Exception $e) {
		$_SESSION['errorMessages'][] = $e;
	}
}

$template = new Template();
$template->blocks[] = new Block('cemeteries/addCemeteryForm.inc');
echo $template->render();