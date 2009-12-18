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
			$search['section'] = $value;
		}
		else {
			if (in_array($field,$knownFields)) {
				$search[$field] = $value;
			}
		}
	}
}

if (count($search)) {
	$internmentList = new InternmentList($search,20,$currentPage);
}


$template = new Template();
$template->blocks[] = new Block('internments/findForm.inc');
if (isset($internmentList)) {
	$template->blocks[] = new Block('internments/internmentList.inc',
									array('internmentList'=>$internmentList));
}
echo $template->render();