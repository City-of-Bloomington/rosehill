<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Cemetery $this->cemetery
 */
$name = View::escape($this->cemetery->getName());
echo "
<cemetery id=\"{$this->cemetery->getId()}\">
	<name>$name</name>
	<sections>
";
	foreach ($this->cemetery->getSections() as $section) {
		$code = View::escape($section->getCode());
		$name = View::escape($section->getName());
		echo "
		<section id=\"{$section->getId()}\">
			<code>$code</code>
			<name>$name</name>
		</section>
		";
	}
echo "
	</sections>
</cemetery>
";
