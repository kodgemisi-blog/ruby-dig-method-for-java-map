require 'json'

file = open(__dir__ + '/sample.json')
json = file.read
map = JSON.parse(json)

puts 'existing ' + map.dig('first', 'second', 'third').to_s

puts 'missing ' + map.dig('first', 'X', 'third').to_s