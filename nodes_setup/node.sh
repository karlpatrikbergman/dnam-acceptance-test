#!/usr/bin/env bash
# Bash knows only status codes (integers) and strings written to the stdout.
# The only way to return a string in bash is with echo
# Integers can be returned using "return"-statement

# Fine debug tool
export PS4='+(${BASH_SOURCE}:${LINENO}): ${FUNCNAME[0]:+${FUNCNAME[0]}(): }'
set -x

function login() {
    local host="$1"
    local port="$2"

    while :
        do
            local session_id_tag=$(curl --silent http://${host}:${port}/getLogin.asp?userName=root&password=root&oneTimeLogin=false)
            local session_id=$(echo "${session_id_tag}" | grep "sessionId" | grep -Eo '[0-9]*')
            if [[ ${session_id} -ne 0 ]]; then
                echo "${session_id}";
                break;
            fi
            sleep 5;
        done
}

function add_board() {
    local host="$1"
    local port="$2"
    local session_id="$3"
    local board_type="$4"
    local url="http://${host}:${port}/mib/eq/board/${board_type}:1:2/create.json?_RFLAGS_=SEMGNOT&_AFLAGS_=AVNP"

    while :
        do
            local response_code=$(curl --write-out %{http_code} --silent --output /dev/null --cookie "sessionId=${session_id}" ${url})
            if [[ ${response_code} -eq 200 ]]; then
                echo "${response_code}"
                break
            fi
            sleep 5;
        done
}