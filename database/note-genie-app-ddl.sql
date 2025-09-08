-- APP DDL ====
BEGIN;

create table application_persona_profile (
    -- todo
);

create index idx on

create table genie_note (
    genie_note_id bigserial primary key,
    note_content_id bigint references note_content(id),
    genie_not_type_id bigint references genie_note_type(id),
    created_timestamp timestamp without time zone,
    app_user_id bigint references app_user(id)
);

create index idx on

-- App Config
create table genie_note_type (
    id bigserial primary key,
    type varchar not null,
    created_timestamp timestamp without time zone
);

create index idx on

create table note_content (
    id bigserial primary key,
    primary_text text not null,
    genie_processed_text varchar not null,
    genie_processed_text_type char(3),
    note_audio_id bigint references voice_audio_basis(id),
    originator_id bigint
);

create index idx on

create table voice_audio_basis (
    id bigserial primary key,
    app_user_id references app_user(id),
    voice_audio blob
);

create index idx on

create table draft_message (
    id bigserial primary key,
    genie_note_id bigint references genie_note(id),
    created_timestamp timestamp without time zone,
    edited_timestamp timestamp without time zone
);

create index idx on

create table sent_message (

);

create index idx on

create table received_message (

);

create index idx on

create table messenger_exchange (

);

create index idx on

create table message_party (

);

create index idx on

create table recipient (

);

create index idx on

create table recipient_group (

);

create index idx on

create table attachment (

);

create index idx on

create table file (

);
create index idx on
ROLLBACK;