select count(*) from tasks_resoluter;

select * from tasks_resolutersession;

select * from tasks_department;

select * from tasks_attempt;

select * from tasks_problemresolution;

select * from tasks_resoluter;

---

select * from tasks_attempt
where resolution_id = 210;

--- Cantidad de resolutores
select count(*) as "Cant. Reso." from tasks_resoluter;

--- Obtiene la catidad de problemas resueltos
--- por cada resolutor.
select distinct resoluter_id as "Resoluter ID" , count(*) as "Cant. Problem." from tasks_problemresolution
group by resoluter_id

--- Obtiene los instentos de los resolutores
--- en los diferentes problemas
select tp.resoluter_id, tp.problem_id, ta.* from tasks_attempt as ta inner join tasks_problemresolution as tp
on ta.resolution_id = tp.id
where ta.resolution_id = 207
order by tp.resoluter_id, tp.id 
