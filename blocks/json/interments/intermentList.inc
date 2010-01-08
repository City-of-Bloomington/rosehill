<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param IntermentList $this->intermentList
 * @param GET sort
 */
$interments = array();
foreach ($this->intermentList as $interment) {
	$last = addslashes($interment->getLastname());
	$first = addslashes($interment->getFirstname());
	$date = $interment->getDeceasedDate('Y-m-d');
	$lot = addslashes($interment->getLot());

	$cemetery_name = '';
	$googleMapUrl = '';
	if ($interment->getCemetery_id()) {
		$cemetery_name = addslashes($interment->getCemetery()->getName());
		$googleMapUrl = addslashes($interment->getCemetery()->getGoogleMapURL());
	}

	$section_code = '';
	$section_name = '';
	if ($interment->getSection_id()) {
		$section_code = addslashes($interment->getSection()->getCode());
		$section_name = addslashes($interment->getSection()->getName());
	}

	$interments[] = "
	{	\"id\":\"{$interment->getId()}\",
		\"lastname\":\"$last\",
		\"firstname\":\"$first\",
		\"deceasedDate\":\"$date\",
		\"cemetery\":{
			\"id\":\"{$interment->getCemetery_id()}\",
			\"name\":\"$cemetery_name\"
		},
		\"section\":{
			\"id\":\"{$interment->getSection_id()}\",
			\"code\":\"$section_code\",
			\"name\":\"$section_name\"
		},
		\"lot\":\"$lot\"
	}
	";
}
$interments = implode(',',$interments);
echo "[$interments]";
