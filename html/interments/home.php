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


$template = isset($_GET['format']) ? new Template('default',$_GET['format']) : new Template();

// Only the HTML version will include the findForm, addForm, and the about page.
if ($template->outputFormat=='html') {
	if (!count($search)) {
		$template->blocks[] = new Block('interments/findForm.inc');
	}
	else {
		$template->blocks[] = new Block('interments/findForm-skinny.inc');
	}

	if (userIsAllowed('Interments') && !count($search)) {
		$return_url = new URL($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']);
		$template->blocks[] = new Block('interments/addIntermentForm.inc',
										array('return_url'=>$return_url));
	}
	$template->blocks['panel-one'][] = new Block('about.inc');
}



// All output formats will include the list of interments
$order = isset($_GET['sort']) ? $_GET['sort'] : null;
if (count($search)) {
	if ($template->outputFormat=='html') {
		$currentPage = isset($_GET['page']) ? (int)$_GET['page'] : 1;
		$intermentList = new IntermentList($search,$order,50,$currentPage);
	}
	else {
		$intermentList = new IntermentList();
		$intermentList->find($search,$order);
	}
	$template->blocks[] = new Block('interments/intermentList.inc',
									array('intermentList'=>$intermentList));
}
elseif ($template->outputFormat!='html') {
	// Since they're using the service, allow them to download the entire data set
	$intermentList = new IntermentList();
	$intermentList->find(null,$order);
	$template->blocks[] = new Block('interments/intermentList.inc',
									array('intermentList'=>$intermentList));
}

echo $template->render();