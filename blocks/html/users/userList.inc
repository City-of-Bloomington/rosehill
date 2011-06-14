<?php
/**
 * @copyright 2007-2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param UserList $this->userList
 */
?>
<div class="interfaceBox">
	<h2><?php
			if (userIsAllowed('Users')) {
				echo "
				<button type=\"button\" class=\"add\" onclick=\"document.location.href='".BASE_URL."/users/addUser.php';\">
					Add User Account
				</button>
				";
			}
		?>
		User Accounts
	</h2>
	<table>
	<?php
		foreach ($this->userList as $user) {
			$editButton = '';
			$deleteButton = '';
			if (userIsAllowed('Users')) {
				$editButton = "
				<button type=\"button\" class=\"edit\" onclick=\"document.location.href='".BASE_URL."/users/updateUser.php?user_id={$user->getId()}';\">
					Edit User Account
				</button>
				";

				$deleteButton = "
				<button type=\"button\" class=\"delete\" onclick=\"document.location.href='".BASE_URL."/users/deleteUser.php?user_id={$user->getId()}';\">
					Delete User Account
				</button>
				";
			}

			echo "
			<tr><td>$editButton</td>
				<td>{$user->getUsername()}</td>
				<td>{$user->getFirstname()} {$user->getLastname()}</td>
				<td>{$user->getAuthenticationMethod()}</td>
				<td>
			";
					foreach ($user->getRoles() as $role) {
						echo "$role ";
					}
			echo "
				</td>
			</tr>
			";
		}
	?>
	</table>
</div>
