<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

$internmentList = new InternmentList();
$internmentList->find();

$template = new Template();
$template->blocks[] = new Block('internments/internmentList.inc',
								array('internmentList'=>$internmentList));
echo $template->render();