alter table people add username varchar(40) unique;
alter table people add password varchar(40);
alter table people add authenticationMethod varchar(40);
alter table people add role varchar(30);

update people p, users u
set p.username=u.username, p.authenticationMethod=u.authenticationMethod
where p.id=u.person_id;

update people set role='Staff';
update people set role='Administrator' where id=1;

drop table user_roles;
drop table roles;
drop table users;
