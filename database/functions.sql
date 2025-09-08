begin;

CREATE FUNCTION initialize_sequence(the_table_name VARCHAR, text)
RETURNS void
AS
$$
begin
 execute format('CREATE SEQUENCE %i START 100', the_table_name);
end;
$$
LANGUAGE plpgsql;