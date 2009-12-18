<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

$deedList = new DeedList();
$deedList->find();

$template = new Template();
$template->blocks[] = new Block('deeds/deedList.inc',array('deedList'=>$deedList));
echo $template->render();