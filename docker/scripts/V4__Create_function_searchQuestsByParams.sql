CREATE OR REPLACE FUNCTION searchByNameAndDescription(pname varchar(20), pdescription varchar(20))
    RETURNS TABLE (
                      id int, name varchar, genre varchar, price money, duration time, max_people int
                  )
    LANGUAGE plpgsql AS
$func$
BEGIN
    RETURN QUERY
        SELECT q.id, q.name, q.genre, q.price, q.duration,  q.max_people from quest as q
        where upper(q.name) LIKE '%' || upper(pname) || '%' and upper(q.description) LIKE '%' || upper(pdescription) || '%';
END
$func$;