require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNArgon2"
  s.version      = package['version']
  s.summary      = package['description']
  s.description  = package['description']
  s.license      = package['license']

  s.authors      = package['author']
  s.homepage     = package['homepage']

  s.source       = { :git => "https://github.com/poowf/react-native-argon2.git", :tag => "v#{s.version}" }
  s.source_files  = "ios/*.{h,m,swift}"
  s.platform          = :ios, '10.0'

  s.dependency 'React-Core'
  s.dependency 'CatCrypto'
end
