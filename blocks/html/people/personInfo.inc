<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Person $this->person
 */
$username = $this->person->getUsername();
if (!$username && userIsAllowed('Users')) {
	$url = BASE_URL.'/users/addUser.php?person_id='.$this->person->getId();
	$username = "
	<button type=\"button\" onclick=\"document.location.href='$url';\">
		Create Account
	</button>";
}


$name = View::escape($this->person->getFullname());
echo "
<h2>$name</h2>
<table>
	<tr><th>Username</th>
		<td>$username</td>
	</tr>
	<tr><th>Email</th>
		<td>{$this->person->getEmail()}</td>
	</tr>
</table>
";
