<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$currentPage = isset($_GET['page']) ? (int)$_GET['page'] : 1;

$deedList = new DeedList(null,20,$currentPage);
$deedList->find();

$template = new Template();
$template->blocks[] = new Block('deeds/deedList.inc',array('deedList'=>$deedList));
echo $template->render();