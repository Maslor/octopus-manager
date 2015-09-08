.output auditx.log
select audit from shell;
drop table shell;
create table shell(name TEXT, shl BLOB, audit VARCHAR(100));
