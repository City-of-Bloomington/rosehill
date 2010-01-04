<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$currentPage = isset($_GET['page']) ? (int)$_GET['page'] : 1;

$knownFields = array('lastname','firstname');
$cemeteries = new CemeteryList();
$cemeteries->find();
foreach ($cemeteries as $cemetery) {
	$knownFields[] = 'section_'.$cemetery->getId();
}


$search = array();
foreach ($_GET as $field=>$value) {
	if ($value) {
		if (false !== strpos($field,'section')) {
			try {
				$section = new Section($value);
				$search['section_id'] = $section->getId();
			}
			catch (Exception $e) {
				// Just ignore any unknown sections
			}
		}
		else {
			if (in_array($field,$knownFields)) {
				$search[$field] = $value;
			}
		}
	}
}

if (count($search)) {
	$intermentList = new IntermentList($search,20,$currentPage);
}


$template = new Template();
$template->blocks[] = new Block('interments/findForm.inc');
if (isset($intermentList)) {
	$template->blocks[] = new Block('interments/intermentList.inc',
									array('intermentList'=>$intermentList));
}
$template->blocks['panel-one'][] = new Block('about.inc');
echo $template->render();