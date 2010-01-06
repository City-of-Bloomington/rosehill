<interments>
<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param IntermentList $this->intermentList
 * @param GET sort
 */
foreach ($this->intermentList as $interment) {
	$last = View::escape($interment->getLastname());
	$first = View::escape($interment->getFirstname());
	$date = $interment->getDeceasedDate('Y-m-d');
	$cemetery_name = View::escape($interment->getCemetery());
	$section = View::escape($interment->getSection());
	$lot = View::escape($interment->getLot());
	echo "
	<interment id=\"{$interment->getId()}\">
		<lastname>$last</lastname>
		<firstname>$first</firstname>
		<deceasedDate>$date</deceasedDate>
		<cemetery id=\"{$interment->getCemetery_id()}\">$cemetery_name</cemetery>
		<section id=\"{$interment->getSection_id()}\">$section</section>
		<lot>$lot</lot>
	</interment>
	";
}
?>
</interments>
