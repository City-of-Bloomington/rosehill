<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$knownFields = array('lastname','firstname','cemetery_id','section_id');

$search = array();
foreach ($_GET as $field=>$value) {
	if ($value) {
		if (in_array($field,$knownFields)) {
			$search[$field] = $value;
		}
	}
}



// Only the HTML version will include the findForm, addForm, and about page.
$template = isset($_GET['format']) ? new Template('default',$_GET['format']) : new Template();
if ($template->outputFormat=='html') {
	$template->blocks[] = new Block('interments/findForm.inc');

	if (userIsAllowed('Interments') && !count($search)) {
		$return_url = new URL($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']);
		$template->blocks[] = new Block('interments/addIntermentForm.inc',
										array('return_url'=>$return_url));
	}
	$template->blocks['panel-one'][] = new Block('about.inc');
}



// All output formats will include the list of interments
if (count($search)) {
	$order = isset($_GET['sort']) ? $_GET['sort'] : null;
	if ($template->outputFormat=='html') {
		$currentPage = isset($_GET['page']) ? (int)$_GET['page'] : 1;
		$intermentList = new IntermentList($search,$order,50,$currentPage);
	}
	else {
		$intermentList = new IntermentList();
		$intermentList->find();
	}
	$template->blocks[] = new Block('interments/intermentList.inc',
									array('intermentList'=>$intermentList));
}

echo $template->render();