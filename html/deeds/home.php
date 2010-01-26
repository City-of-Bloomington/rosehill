<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$currentPage = isset($_GET['page']) ? (int)$_GET['page'] : 1;

$deedList = new DeedList(null,20,$currentPage);


$knownFields = array('section_id','lot','cemetery_id','firstname','lastname','middleInitial');
if (count(array_intersect(array_keys($_GET),$knownFields))) {
	$deedList->find($_GET);
}
else {
	$deedList->find();
}


$template = new Template();
$template->blocks[] = new Block('deeds/findForm.inc');
$template->blocks[] = new Block('deeds/deedList.inc',array('deedList'=>$deedList));
echo $template->render();