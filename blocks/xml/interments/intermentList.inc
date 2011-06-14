<interments>
<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param IntermentList $this->intermentList
 * @param GET sort
 */
foreach ($this->intermentList as $interment) {
	$last = View::escape($interment->getLastname());
	$first = View::escape($interment->getFirstname());
	$date = $interment->getDeceasedDate('Y-m-d');
	$lot = View::escape($interment->getLot());

	$cemetery_name = '';
	if ($interment->getCemetery_id()) {
		$cemetery_name = View::escape($interment->getCemetery()->getName());
	}

	$section_code = '';
	$section_name = '';
	if ($interment->getSection_id()) {
		$section_code = View::escape($interment->getSection()->getCode());
		$section_name = View::escape($interment->getSection()->getName());
	}
	echo "
	<interment id=\"{$interment->getId()}\">
		<lastname>$last</lastname>
		<firstname>$first</firstname>
		<deceasedDate>$date</deceasedDate>
		<cemetery id=\"{$interment->getCemetery_id()}\">
			<name>$cemetery_name</name>
		</cemetery>
		<section id=\"{$interment->getSection_id()}\">
			<code>$section_code</code>
			<name>$section_name</name>
		</section>
		<lot>$lot</lot>
	</interment>
	";
}
?>
</interments>
